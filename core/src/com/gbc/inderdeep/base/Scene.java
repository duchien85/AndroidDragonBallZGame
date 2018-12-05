package com.gbc.inderdeep.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gbc.inderdeep.utils.SceneSegment;

import java.util.ArrayList;

/**
 * Created by markapptist on 2018-11-15.
 */

public class Scene extends Actor {

    private ArrayList<SceneSegment> segmentList;
    private int index;

    public Scene() {
        super();

        segmentList = new ArrayList<SceneSegment>();
        index = -1;
    }

    public void addSegment(SceneSegment segment) {
        segmentList.add(segment);
    }

    public void clearSegment() {
        segmentList.clear();
    }

    public void start() {
        index = 0;
        segmentList.get(index).start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(isSegmentFinished() && !isLastSegment())
            loadNextSegment();
    }

    public boolean isSegmentFinished() {
        return segmentList.get(index).isFinished();
    }

    public boolean isLastSegment() {
        return (index >= segmentList.size() - 1);
    }

    public void loadNextSegment() {
        if(isLastSegment())
            return;

        segmentList.get(index).finish();
        index++;
        segmentList.get(index).start();
    }

    public boolean isSceneFinished() {
        return (isLastSegment() && isSegmentFinished());
    }
}