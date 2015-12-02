package cz.muni.fi.pv254.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class UserEntryListDTO {
    private List<UserEntryDTO> entries;

    public UserEntryListDTO(List<UserEntryDTO> entries) {
        this.entries = entries;
    }
}
