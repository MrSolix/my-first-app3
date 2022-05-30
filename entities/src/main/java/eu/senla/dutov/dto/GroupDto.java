package eu.senla.dutov.dto;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {

    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupDto)) {
            return false;
        }
        GroupDto groupDto = (GroupDto) o;
        return Objects.equals(id, groupDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
