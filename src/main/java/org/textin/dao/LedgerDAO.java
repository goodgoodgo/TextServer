package org.textin.dao;

import org.apache.ibatis.annotations.Param;
import org.textin.model.dto.LedgerDTO;
import org.textin.model.entity.Ledger;
import org.textin.model.vo.LedgerVO;

import java.util.List;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-26 09:13
 */
public interface LedgerDAO {
    List<Ledger> get(@Param("userId") Long userId);
    void delete(@Param("userId") Long userId,@Param("ledgerId") Long ledgerId);
    void update(@Param("userId") Long userId,@Param("ledgerId") Long ledgerId,@Param("name") String name);
    //void updateTop(@Param("userId") Long userId,@Param("ledgerId") Long ledgerId);
    void save(Ledger ledger);
}
