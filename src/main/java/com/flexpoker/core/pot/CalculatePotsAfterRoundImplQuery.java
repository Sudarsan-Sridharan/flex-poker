package com.flexpoker.core.pot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.flexpoker.config.Query;
import com.flexpoker.core.api.pot.CalculatePotsAfterRoundQuery;
import com.flexpoker.model.Pot;
import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;
import com.flexpoker.util.OpenPotPredicate;

@Query
public class CalculatePotsAfterRoundImplQuery implements CalculatePotsAfterRoundQuery {

    @Override
    public Set<Pot> execute(Table table) {
        Set<Integer> chipsInFrontSet = new HashSet<>();
        for (Seat seat : table.getSeats()) {
            if (seat.getChipsInFront() != 0) {
                chipsInFrontSet.add(seat.getChipsInFront());
            }
        }

        List<Integer> chipsInFrontList = new ArrayList<Integer>(chipsInFrontSet);
        Collections.sort(chipsInFrontList);

        List<Integer> maxContributionPerSeatPerPot = new ArrayList<Integer>();
        maxContributionPerSeatPerPot.add(chipsInFrontList.get(0));
        for (int i = 0; i < chipsInFrontList.size() - 1; i++) {
            maxContributionPerSeatPerPot.add(
                    chipsInFrontList.get(i + 1) - chipsInFrontList.get(i));
        }

        Set<Pot> pots = new HashSet<>(table.getCurrentHand().getPots());

        for (Integer chipsPerLevel : maxContributionPerSeatPerPot) {
            Pot pot = fetchOpenPot(pots);
            if (pot.getAmount() == 0) {
                pots.add(pot);
            }

            for (Seat seat : table.getSeats()) {
                if (seat.getChipsInFront() == 0) {
                    continue;
                }

                if (!pot.getSeats().contains(seat) && seat.isStillInHand()) {
                    pot.getSeats().add(seat);
                }
                pot.addChips(chipsPerLevel);
                seat.setChipsInFront(seat.getChipsInFront() - chipsPerLevel);
                if (seat.isAllIn()) {
                    pot.closePot();
                }
            }
        }
        
        return pots;
    }
    
    private Pot fetchOpenPot(Set<Pot> pots) {
        Pot pot = (Pot) CollectionUtils.find(pots, new OpenPotPredicate());
        if (pot == null) {
            pot = new Pot();
        }
        return pot;
    }

}