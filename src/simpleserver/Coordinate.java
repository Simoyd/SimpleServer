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
package simpleserver;

import simpleserver.nbt.NBTByte;
import simpleserver.nbt.NBTCompound;
import simpleserver.nbt.NBTInt;

public class Coordinate {
  private int x;
  private byte y;
  private int z;
  private Dimension dimension;
  private final int hashCode;

  public Coordinate(int x, byte y, int z) {
    this(x, y, z, Dimension.EARTH);
  }

  public Coordinate(int x, byte y, int z, Player player) {
    this(x, y, z, player.getDimension());
  }

  public Coordinate(NBTCompound tag) {
    this(tag.getInt("x").get(),
         tag.getByte("y").get(),
         tag.getInt("z").get(),
         Dimension.get(tag.getByte("dimension").get()));
  }

  public Coordinate(int x, byte y, int z, Dimension dimension) {
    this.dimension = dimension;
    this.x = x;
    this.y = y;
    this.z = z;

    int code = 17;
    code = 37 * code + x;
    code = 37 * code + y;
    code = 37 * code + z;
    code = 37 * code + dimension.ordinal();
    hashCode = code;
  }

  public Coordinate(int x, int y, int z, Dimension dimension) {
    this(x, (byte) y, z, dimension);
  }

  public Coordinate(int x, int y, int z) {
    this(x, (byte) y, z);
  }

  public int x() {
    return x;
  }

  public byte y() {
    return y;
  }

  public int z() {
    return z;
  }

  public Dimension dimension() {
    return dimension;
  }

  public Coordinate setX(int x) {
    return new Coordinate(x, y, z, dimension);
  }

  public Coordinate setY(byte y) {
    return new Coordinate(x, y, z, dimension);
  }

  public Coordinate setY(int y) {
    return setY((byte) y);
  }

  public Coordinate setZ(int z) {
    return new Coordinate(x, y, z, dimension);
  }

  public Coordinate setDimension(Dimension dimension) {
    return new Coordinate(x, y, z, dimension);
  }

  public Coordinate add(int x, byte y, int z) {
    return new Coordinate(this.x + x,
                          this.y + y,
                          this.z + z,
                          dimension);
  }

  public Coordinate add(int x, int y, int z) {
    return add(x, (byte) y, z);
  }

  public boolean equals(Coordinate coordinate) {
    return (coordinate.x == x) && (coordinate.y == y) && (coordinate.z == z) && coordinate.dimension == dimension;
  }

  @Override
  public boolean equals(Object object) {
    return (object instanceof Coordinate) && equals((Coordinate) object);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public String toString() {
    return String.format("%d,%d,%d", x, y, z);
  }

  public NBTCompound tag() {
    NBTCompound tag = new NBTCompound("coordinate");
    tag.put(new NBTInt("x", x));
    tag.put(new NBTByte("y", y));
    tag.put(new NBTInt("z", z));
    tag.put(new NBTByte("dimension", dimension.index()));
    return tag;
  }

  public enum Dimension {
    EARTH((byte) 0),
    NETHER((byte) -1),
    SKY((byte) 1),
    LIMBO;

    private byte index;

    Dimension(byte index) {
      this.index = index;
    }

    Dimension() {
    }

    @Override
    public String toString() {
      String name = super.toString();
      return name.substring(0, 1) + name.substring(1).toLowerCase();
    }

    private boolean isNamed(String name) {
      return super.toString().equals(name.toUpperCase());
    }

    public byte index() {
      return index;
    }

    public static Dimension get(byte index) {
      for (Dimension dim : Dimension.values()) {
        if (dim.index == index) {
          return dim;
        }
      }
      return Dimension.LIMBO;
    }

    public static Dimension get(String name) {
      for (Dimension dim : Dimension.values()) {
        if (dim.isNamed(name)) {
          return dim;
        }
      }
      return Dimension.LIMBO;
    }

  }
}
