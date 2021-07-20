package com.fatma.eval.dto;

public class Action {
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                '}';
    }
}
