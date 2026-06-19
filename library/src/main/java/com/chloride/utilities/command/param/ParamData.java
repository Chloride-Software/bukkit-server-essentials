package com.chloride.utilities.command.param;

import com.chloride.utilities.command.ParamType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamData {

    private String name;
    private String defaultValue;
    private ParamType type;

}
