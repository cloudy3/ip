package models;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : " ");  // return tick symbol if done
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + getDescription());
    }

}