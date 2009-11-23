package com.flexpoker.bso;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;
import com.flexpoker.util.Context;
import com.flexpoker.util.DataUtilsForTests;


public class SeatStatusBsoImplTest {

    private SeatStatusBsoImpl bso = (SeatStatusBsoImpl) Context.instance()
            .getBean("seatStatusBso");

    @Test
    public void testSetStatusForNewGame() {
        Table table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, 2);
        bso.setStatusForNewGame(table);

        assertTrue(table.getSeats().get(0).isStillInHand());
        assertTrue(table.getSeats().get(1).isStillInHand());

        // since things are assigned randomly, need to do some if/else logic
        if (table.getButton().equals(table.getSeats().get(0))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(0)));
        } else if (table.getButton().equals(table.getSeats().get(1))){
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(1)));
        } else {
            fail("None of the seats were the button.");
        }

        table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, 3);
        bso.setStatusForNewGame(table);

        assertTrue(table.getSeats().get(0).isStillInHand());
        assertTrue(table.getSeats().get(1).isStillInHand());
        assertTrue(table.getSeats().get(2).isStillInHand());

        // since things are assigned randomly, need to do some if/else logic
        if (table.getButton().equals(table.getSeats().get(0))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(2)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(0)));
        } else if (table.getButton().equals(table.getSeats().get(1))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(2)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(1)));
        } else if (table.getButton().equals(table.getSeats().get(2))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(2)));
        } else {
            fail("None of the seats were the button.");
        }

        table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, 6);
        bso.setStatusForNewGame(table);

        assertTrue(table.getSeats().get(0).isStillInHand());
        assertTrue(table.getSeats().get(1).isStillInHand());
        assertTrue(table.getSeats().get(2).isStillInHand());
        assertTrue(table.getSeats().get(3).isStillInHand());
        assertTrue(table.getSeats().get(4).isStillInHand());
        assertTrue(table.getSeats().get(5).isStillInHand());

        // since things are assigned randomly, need to do some if/else logic
        if (table.getButton().equals(table.getSeats().get(0))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(2)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(3)));
        } else if (table.getButton().equals(table.getSeats().get(1))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(2)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(3)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(4)));
        } else if (table.getButton().equals(table.getSeats().get(2))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(3)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(4)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(5)));
        } else if (table.getButton().equals(table.getSeats().get(3))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(4)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(5)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(0)));
        } else if (table.getButton().equals(table.getSeats().get(4))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(5)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(1)));
        } else if (table.getButton().equals(table.getSeats().get(5))) {
            assertTrue(table.getSmallBlind().equals(table.getSeats().get(0)));
            assertTrue(table.getBigBlind().equals(table.getSeats().get(1)));
            assertTrue(table.getActionOn().equals(table.getSeats().get(2)));
        } else {
            fail("None of the seats were the button.");
        }
    }

    @Test
    public void testStatusForNewHand() {
        setStatusForNewHandHelper(2, Arrays.asList(new Integer[]{}), 1, 1, 0, 1);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{0}), 2, 2, 1, 2);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{1}), 2, 2, 0, 2);
        setStatusForNewHandHelper(3, Arrays.asList(new Integer[]{2}), 1, 1, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0}), 1, 2, 3, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{1}), 1, 2, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{2}), 1, 2, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{3}), 1, 2, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0, 1}), 2, 2, 3, 2);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{1, 2}), 0, 0, 3, 0);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{2, 3}), 1, 1, 0, 1);
        setStatusForNewHandHelper(4, Arrays.asList(new Integer[]{0, 3}), 2, 2, 1, 2);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{}), 1, 2, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{2}), 1, 2, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{2, 3}), 1, 2, 4, 0);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{0, 1, 2}), 4, 4, 3, 4);
        setStatusForNewHandHelper(5, Arrays.asList(new Integer[]{0, 1, 4}), 2, 2, 3, 2);
    }

    @Test
    public void testStatusForNewRound() {
        setStatusForNewRoundHelper(2, Arrays.asList(new Integer[]{}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(3, Arrays.asList(new Integer[]{0}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(3, Arrays.asList(new Integer[]{1}), Arrays.asList(new Integer[]{}), 2);
        setStatusForNewRoundHelper(3, Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{0}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{1}), Arrays.asList(new Integer[]{}), 2);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{3}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{0, 1}), Arrays.asList(new Integer[]{}), 2);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{}), 3);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{2, 3}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{0, 3}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{2, 3}), Arrays.asList(new Integer[]{}), 1);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{0, 1, 2}), Arrays.asList(new Integer[]{}), 3);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{0, 1, 4}), Arrays.asList(new Integer[]{}), 2);

        // add some users that have dropped out during the hand, but not necessarily left the table
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{0}), Arrays.asList(new Integer[]{1}), 2);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{1}), Arrays.asList(new Integer[]{2}), 3);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{0}), 1);
        setStatusForNewRoundHelper(4, Arrays.asList(new Integer[]{3}), Arrays.asList(new Integer[]{1}), 2);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{}), Arrays.asList(new Integer[]{0, 1, 2}), 3);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{2}), Arrays.asList(new Integer[]{0, 1}), 3);
        setStatusForNewRoundHelper(5, Arrays.asList(new Integer[]{2, 3}), Arrays.asList(new Integer[]{1}), 4);
    }

    private void setStatusForNewHandHelper(int numberOfPlayers,
            List<Integer> seatsThatJustLeft, int buttonIndex,
            int smallBlindIndex, int bigBlindIndex, int actionOnIndex) {
        Table table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, numberOfPlayers);
        table.setButton(table.getSeats().get(0));
        table.setSmallBlind(table.getSeats().get(1));
        table.setBigBlind(table.getSeats().get(2));

        for (Integer seat : seatsThatJustLeft) {
            table.getSeats().get(seat.intValue()).setUserGameStatus(null);
            table.getSeats().get(seat.intValue()).setPlayerJustLeft(true);
        }

        bso.setStatusForNewHand(table);

        for (int i = 0; i < numberOfPlayers; i++) {
            if (seatsThatJustLeft.contains(i)) {
                assertFalse(table.getSeats().get(i).isStillInHand());
            } else {
                assertTrue(table.getSeats().get(i).isStillInHand());
            }
        }

        assertTrue(table.getButton().equals(table.getSeats().get(buttonIndex)));
        assertTrue(table.getSmallBlind().equals(table.getSeats().get(smallBlindIndex)));
        assertTrue(table.getBigBlind().equals(table.getSeats().get(bigBlindIndex)));
        assertTrue(table.getActionOn().equals(table.getSeats().get(actionOnIndex)));
    }

    private void setStatusForNewRoundHelper(int numberOfPlayers,
            List<Integer> seatsThatJustLeft, List<Integer> seatsNotInHand,
            int actionOnIndex) {
        Table table = new Table();
        DataUtilsForTests.fillTableWithUsers(table, numberOfPlayers);
        table.setButton(table.getSeats().get(0));
        table.setSmallBlind(table.getSeats().get(1));
        table.setBigBlind(table.getSeats().get(2));

        for (Integer seat : seatsThatJustLeft) {
            table.getSeats().get(seat.intValue()).setUserGameStatus(null);
            table.getSeats().get(seat.intValue()).setPlayerJustLeft(true);
        }

        for (Seat seat : table.getSeats()) {
            if (seat.getUserGameStatus() != null) {
                seat.setStillInHand(true);
            }
        }

        for (Integer seat : seatsNotInHand) {
            table.getSeats().get(seat.intValue()).setStillInHand(false);
        }

        bso.setStatusForNewRound(table);
        assertTrue(table.getActionOn().equals(table.getSeats().get(actionOnIndex)));
    }

}