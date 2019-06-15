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
@Table(name = "t_blog_article")
@Excel("博客文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TITLE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String title;

    @Column(name = "CATEGORY_ID")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String categoryId;

    @Column(name = "STATUS")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private Double status;

    @Column(name = "WXPAY_QRCODE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String WxPayQrCode;

    @Column(name = "MD_CONTENT")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String mdContent;

    @Column(name = "HTML_CONTENT")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String htmlContent;

    @Column(name = "COVER")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String cover;

    @Column(name = "SUB_MESSAGE")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String subMessage;

    @Column(name = "VIEWNUM")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private Double viewNum;

    @Column(name = "IS_ENCRYPT")
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private Double isEcrypt;

    @Column(name = "ORDER_NUM")
    private Double orderNum;

    @Column(name = "CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    @Column(name = "DELETE_TIME")
    @ExcelField(value = "删除时间", writeConverter = TimeConverter.class)
    private Date deleteTime;

    @Column(name = "PUBLISH_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date publishTime;

    @Transient
    private String createTimeFrom;
    @Transient
    private String createTimeTo;

}
