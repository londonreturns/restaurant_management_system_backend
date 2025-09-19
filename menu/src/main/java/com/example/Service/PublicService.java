package com.example.Service;

import com.example.Dto.MenuDTO;
import com.example.Dto.SizeGroupOptionGroupDTO;

public interface PublicService {
    MenuDTO menuInfo(Long menuId);

    MenuDTO menuInfoBetter(Long menuId);

    SizeGroupOptionGroupDTO extraPrices(Long sizeGroupOptionGroupId);

    SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO);
}
