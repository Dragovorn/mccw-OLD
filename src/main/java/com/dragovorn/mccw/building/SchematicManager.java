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

    private final long delay = 1L;

    private final int blocksPerTime = 1;

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
        NBTInputStream nbtInputStream = new NBTInputStream(stream);

        CompoundTag schematicTag = (CompoundTag) nbtInputStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new InvalidSchematicException(file.getName() + " has no existing Schematic tag or is not first");
        }

        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new InvalidSchematicException(file.getName() + " is missing a Blocks tag!");
        }

        byte[] blockId = getChildTag(file, schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(file, schematic, "Data", ByteArrayTag.class).getValue();
        byte[] addId = new byte[0];

        short width = getChildTag(file, schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(file, schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(file, schematic, "Height", ShortTag.class).getValue();
        short[] blocks = new short[blockId.length];

        if (schematic.containsKey("AddBlocks")) {
            addId = getChildTag(file, schematic, "AddBlocks", ByteArrayTag.class).getValue();
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

    private <T extends Tag> T getChildTag(File file, Map<String, Tag> tags, String key, Class<T> expected) {
        if (!tags.containsKey(key)) {
            throw new InvalidSchematicException(file.getName() + " is missing a " + key + " tag");
        }

        Tag tag = tags.get(key);

        if(!expected.isInstance(tag)) {
            throw new InvalidSchematicException(key + " is not of tag type " + expected.getName());
        }

        return expected.cast(tag);
    }
}