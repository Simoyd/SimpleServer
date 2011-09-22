/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.command;

import java.util.ArrayList;
import java.util.HashMap;

import simpleserver.Color;
import simpleserver.Player;
import simpleserver.Player.Action;

public class LockCommand extends AbstractCommand implements PlayerCommand {
  public LockCommand() {
    super("lock [name|list]", "Create or list locked chests");
  }

  public void execute(Player player, String message) {
    String name = extractArgument(message);
    if (name == null) {
      if (player.isAttemptLock()) {
        player.setAttemptedAction(null);
        player.addTMessage(Color.GRAY, "Chests you place or open will no longer be locked.");
        return;
      }
    }
    if (name != null && name.equals("list")) {
      HashMap<String, HashMap<ArrayList<String>, Integer>> list = player.getServer().data.chests.chestList(player);
      if (list.size() == 0) {
        player.addTMessage(Color.GRAY, "You don't have any locked chests.");
      } else {
        player.addTMessage(Color.GRAY, "Your locked chests:");
        for (String current : list.keySet()) {
          for (ArrayList<String> cur : list.get(current).keySet()) {
            String keysText = "Keys: ";
            if (cur.size() > 0) {
              boolean first = true;
              for (String curKey : cur) {
                if (first) {
                  first = false;
                } else {
                  keysText += ", ";
                }
                keysText += curKey;
              }
            } else {
              keysText += "NONE";
            }
            player.addTMessage(Color.WHITE, "%s %s - %s" + keysText, list.get(current).get(cur), current, Color.GRAY);
          }
        }
      }
    } else {
      if (name != null && name.length() > 16) {
        player.addTMessage(Color.RED, "Names longer than 16 characters are not allowed.");
        return;
      }
      player.addTMessage(Color.GRAY, "Create or open a chest, and it will be locked to you.");
      player.setAttemptedAction(Action.Lock);
      player.setChestArgument(name);
    }
  }
}
