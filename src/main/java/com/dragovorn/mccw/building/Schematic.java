package com.dragovorn.mccw.building;

public class Schematic {

    private byte[] data;

    private short[] blocks;
    private short width;
    private short length;
    private short height;

    private String name;

    Schematic(String name, short[] blocks, byte[] data, short width, short length, short height) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public void setWidth(short width) {
        this.width = width;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public short[] getBlocks() {
        return this.blocks;
    }

    public short getWidth() {
        return this.width;
    }

    public short getLength() {
        return this.length;
    }

    public short getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }
}