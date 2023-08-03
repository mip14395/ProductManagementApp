package domain.model;

import java.util.ArrayList;
import java.util.List;

import presentation.Subscriber;

public class Publisher {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void notifySubscribers() {
        for (Subscriber s : subscribers)
            s.update();
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void addSubscriber(Subscriber subscriber) {
        if (subscriber == null)
            throw new NullPointerException();
        if (!subscribers.contains(subscriber))
            subscribers.add(subscriber);
    }

    public List<Subscriber> getSubscribers() {
        return this.subscribers;
    }
}
