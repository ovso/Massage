package io.github.ovso.massage.framework;

import lombok.experimental.Accessors;

public class SelectableItem<T> {
  @Accessors(chain = true) private T item;
  @Accessors(chain = true) private boolean isFavorite = false;
}
