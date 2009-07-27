package com.flexpoker.bso;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexpoker.model.Game;
import com.flexpoker.model.RealTimeGame;

@Transactional
@Service("realTimeGameBso")
public class RealTimeGameBsoImpl extends HashMap<Game, RealTimeGame>
        implements RealTimeGameBso {

    private static final long serialVersionUID = 5614346056128935854L;

}
