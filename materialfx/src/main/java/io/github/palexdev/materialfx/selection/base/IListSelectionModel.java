/*
 *     Copyright (C) 2021 Parisi Alessandro
 *     This file is part of MaterialFX (https://github.com/palexdev/MaterialFX).
 *
 *     MaterialFX is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     MaterialFX is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with MaterialFX.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.materialfx.selection.base;

import io.github.palexdev.materialfx.controls.base.AbstractMFXFlowlessListCell;
import javafx.beans.property.ListProperty;
import javafx.scene.input.MouseEvent;

public interface IListSelectionModel<T> {
    void select(AbstractMFXFlowlessListCell<T> item, MouseEvent mouseEvent);
    void clearSelection();
    AbstractMFXFlowlessListCell<T> getSelectedItem();
    ListProperty<AbstractMFXFlowlessListCell<T>> getSelectedItems();
    boolean allowsMultipleSelection();
    void setAllowsMultipleSelection(boolean multipleSelection);
}
