package cc.mrbird.febs.system.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_link")
public class Link implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "LINK_ID")
    private Long linkId;

    @Column(name = "LINK_SHORTNAME")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String linkShortName;

    @Column(name = "LINK_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String linkName;

    @Column(name = "LINK_ADDRESS")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String linkAddress;

    @Column(name = "LINK_TYPE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private Double linkType;

    @Column(name = "LINK_IMG")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String linkImg;

    @Column(name = "ORDER_NUM")
    private Double orderNum;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

}