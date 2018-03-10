package viewer.utilities;

import javafx.event.Event;
import javafx.event.EventHandler;

public class RichHandler<T> extends RichEvent<T> implements EventHandler<Event> {
	EventHandler<Event> eventhandler;
	public RichHandler(EventHandler<Event> eventhandler, T data) {
		super(data);
		this.eventhandler = eventhandler;
	}
	public RichEvent<T> ToEvent() {
		return new RichEvent(this.GetData());
	}
	
	@Override
	public void handle(Event event) {
		this.eventhandler.handle(this.ToEvent());
	}
}
