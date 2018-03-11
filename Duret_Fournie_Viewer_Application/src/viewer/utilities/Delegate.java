package viewer.utilities;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Delegate<T extends Event> implements EventHandler<T> {
	List<EventHandler<T>> subscribers;
	public Delegate() {
		this.subscribers = new ArrayList<>();
	}

	@Override
	public void handle(T event) {
		this.Call(event);
	}
	
	public void Subscribe(EventHandler<T> eventhandler) {
		this.subscribers.add(eventhandler);
	}
	public void Unsubscribe(EventHandler<T> eventhandler) {
		this.subscribers.remove(eventhandler);
	}


	public void Call(T event) {
		for (EventHandler<T> eventhandler : this.subscribers) {
			eventhandler.handle(event);
		}
	}
}
