package at.jku.oeh.lan.laganizer.model.dao;

import java.util.function.Consumer;

import javax.transaction.Transactional;

public interface TransactionalConsumer<T> extends Consumer<T> {
	@Transactional
	@Override
	public void accept(T t);
}
