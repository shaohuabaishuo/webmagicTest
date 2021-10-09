package fund;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * {
 *     "NAV":"9.5644",
 *     "NAVCHGRT":"-1.59",
 *     "CHANGERATIO":"--",
 *     "GZTIME":"2021-01-12 15:00",
 *     "SHORTNAME":"xx",
 *     "NEWPRICE":"--",
 *     "HQDATE":"--",
 *     "FCODE":"110011",
 *     "ISHAVEREDPACKET":false,
 *     "GSZ":"9.7629",
 *     "GSZZL":"2.08",
 *     "ACCNAV":"10.4544",
 *     "PDATE":"2021-01-11",
 *     "ZJL":"--"
 * }
 */
@Setter
@Getter
public class Fund {
    //最新的净值日期
    private LocalDateTime PDATE;
    //最新净值
    private String NAV;
    //最新的净值涨了多少
    private String NAVCHGRT;
    //估值
    private String GSZ;
    //估值涨了多少
    private String GSZZL;
    //估值的日期
    private LocalDateTime GZTIME;
    //名称
    private String SHORTNAME;
    //编码
    private String FCODE;
    private String ACCNAV;
}
