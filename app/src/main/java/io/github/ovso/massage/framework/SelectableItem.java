package io.github.ovso.massage.framework;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 10. 25
 */

@Data public class SelectableItem<T> {
  @Accessors(chain = true) private T item;
  @Accessors(chain = true) private boolean isFavorite = false;
}
