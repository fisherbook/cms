package net.stackoverflow.cms.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 主键列表VO类
 *
 * @author 凉衫薄
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdsDTO {

    @NotEmpty(message = "ids不能为空")
    private List<String> ids;
}
