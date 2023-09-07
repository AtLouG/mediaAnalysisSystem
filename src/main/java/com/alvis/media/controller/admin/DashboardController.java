package com.alvis.media.controller.admin;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.RestResponse;
import com.alvis.media.viewmodel.admin.dashboard.IndexVM;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
@AllArgsConstructor
public class DashboardController extends BaseApiController {

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();


        return RestResponse.ok(vm);
    }
}
