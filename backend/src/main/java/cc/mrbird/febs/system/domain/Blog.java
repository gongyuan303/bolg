package cc.mrbird.febs.system.domain;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_blog_config")
@Excel("博客配置表")
public class Blog implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "BLOG_ID")
    private Long blogId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "BLOG_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String blogName;

    @Column(name = "AVATAR")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String avatar;

    @Column(name = "SIGN")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String sign;

    @Column(name = "WXPAY_QRCODE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String WxPayQrCode;

    @Column(name = "AliPAY_QRCODE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String aliPayQrCode;

    @Column(name = "GTIHUB_ADDRESS")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String githubAddress;

    @Column(name = "VIEW_PASSWORD")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String viewPassword;

    @Column(name = "VIEW_SALT")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String viewSalt;

    @Column(name = "ORDER_NUM")
    private Double orderNum;

    @Column(name = "CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    @Transient
    private String createTimeFrom;
    @Transient
    private String createTimeTo;

}
