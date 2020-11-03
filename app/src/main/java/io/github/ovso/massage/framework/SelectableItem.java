package io.github.ovso.massage.framework;

import lombok.experimental.Accessors;

public class SelectableItem<T> {
  @Accessors(chain = true) public T item;
  @Accessors(chain = true) public boolean isFavorite = false;
}
