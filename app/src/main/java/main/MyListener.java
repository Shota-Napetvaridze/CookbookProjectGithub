package main;

import model.Recipe;

public interface MyListener {
    public void onClickListener(Recipe recipe);
    public void favClickListener(Recipe recipe);
}
