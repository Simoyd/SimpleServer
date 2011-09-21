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

import simpleserver.Color;
import simpleserver.Player;
import simpleserver.Player.Action;

public class KeyCommand extends AbstractCommand implements PlayerCommand {
  public KeyCommand() {
    super("key PLAYER", "Create or revoke key to a locked chest");
  }

  public void execute(Player player, String message) {
    String[] arguments = extractArguments(message);
    if ((arguments.length == 0) || (arguments[0] == null)) {
      player.addTMessage(Color.RED, "No player specified");
      return;
    }
    String targetPlayer = arguments[0];
    if (targetPlayer.length() > 16) {
      player.addTMessage(Color.RED, "Names longer than 16 characters are not allowed.");
      return;
    }
    player.addTMessage(Color.GRAY, "Open a locked chest to toggle access for %s.", targetPlayer);
    player.setAttemptedAction(Action.Key);
    player.setChestArgument(targetPlayer);
  }
}
