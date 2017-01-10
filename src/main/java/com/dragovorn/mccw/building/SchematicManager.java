package com.dragovorn.mccw.building;

import com.dragovorn.mccw.exceptions.EmptyFolderException;
import com.dragovorn.mccw.exceptions.InvalidSchematicException;
import com.dragovorn.mccw.exceptions.SchematicNotFoundException;
import com.google.common.collect.ImmutableList;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchematicManager {

    private List<Schematic> schematics;

    public SchematicManager() {
        this.schematics = new ArrayList<>();
    }

    public void loadSchematics(File file) {
        this.schematics.clear();

        if (file.listFiles() == null) {
            throw new EmptyFolderException(file.getName() + " is empty");
        }

        for (File files : file.listFiles()) {
            if (!files.getName().startsWith(".")) {
                try {
                    Schematic schematic = loadSchematic(files);

                    if (schematic != null) {
                        this.schematics.add(schematic);
                    }
                } catch (IOException exception) {
                    throw new InvalidSchematicException(exception);
                }
            }
        }
    }

    public ImmutableList<Schematic> getSchematics() {
        return new ImmutableList.Builder<Schematic>().addAll(schematics).build();
    }

    public Schematic getSchematicByName(String name) {
        for (Schematic schematic : this.schematics) {
            if (schematic.getName().equals(name)) {
                return schematic;
            }
        }

        throw new SchematicNotFoundException(name + " hasn't been loaded!");
    }

    public Schematic getSchematic(int id) {
        return schematics.get(id);
    }

    private Schematic loadSchematic(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(stream);

        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }

        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }

        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();

        byte[] blockId = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        byte[] addId = new byte[0];
        short[] blocks = new short[blockId.length];

        if (schematic.containsKey("AddBlocks")) {
            addId = getChildTag(schematic, "AddBlocks", ByteArrayTag.class).getValue();
        }

        for (int index = 0; index < blockId.length; index++) {
            if ((index >> 1) >= addId.length) {
                blocks[index] = (short) (blockId[index] & 0xFF);
            } else {
                if ((index & 1) == 0) {
                    blocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blockId[index] & 0xFF));
                } else {
                    blocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blockId[index] & 0xFF));
                }
            }
        }

        return new Schematic(file.getName().replace(".schematic", ""), blocks, blockData, width, length, height);
    }

    private <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}