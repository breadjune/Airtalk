package com.mobilepark.airtalk.service;

import com.mobilepark.airtalk.data.AdminGroup;
import com.mobilepark.airtalk.data.AdminGroupAuth;
import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.repository.AdminGroupRepository;
import com.mobilepark.airtalk.repository.AdminGroupAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminGroupService {
    private static final Logger logger = LoggerFactory.getLogger(AdminGroupService.class);

    @Autowired
    AdminGroupRepository authGroupRepository;

    @Autowired
    AdminGroupAuthRepository adminGroupAuthRepository;

    @Autowired
    MenuService menuService;

    public List<AdminGroup> search(){
        List<AdminGroup> list = new ArrayList<>();

        try {
            list = authGroupRepository.findAll();
        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return list;
    }

    // public Group findByAuthGroupSeq(int authGroupSeq){
    //     Group authGroup = authGroupRepository.nativeFindByAdminGroupSeq(adminGroupSeq);
    //     return authGroup;
    // }

    // public List<AdminGroupAuth> searchAdminGroupAuth(int adminGroupSeq) {
    //     List<AdminGroupAuth> adminGroupAuthList = new ArrayList<>();

    //     try {
    //         adminGroupAuthList = adminGroupAuthRepository.findByAdminGroupSeq(adminGroupSeq, new Sort(Sort.Direction.DESC, "seq"));
    //     } catch (Exception e) {
    //         logger.error(e.getMessage());
    //     }

    //     return adminGroupAuthList;
    // }

    @Transactional
    public AdminGroup create(String name, String description, String arrayAuth , String menuSeq) {
        AdminGroup AdminGroup = null;
        // AuthGroup AuthGroup = null;
        // JSONObject jsonObject = null;
        
        System.out.println("auth: " + arrayAuth);

        try {
            /* 그룹 등록 처리 START */
            AdminGroup = new AdminGroup();
            AdminGroup.setName(name);
            AdminGroup.setDescription(description);
            AdminGroup.setRegDate(new Date());

            AdminGroup = authGroupRepository.save(AdminGroup);
            /* 그룹 등록 처리 END */

            /* 그룹 권한 등록 처리 START */
            this.adminGroupAuthChange(AdminGroup.getAdminGroupSeq(), arrayAuth, menuSeq);
            /* 그룹 권한 등록 처리 END */
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return AdminGroup;
    }

    @Transactional
    public AdminGroup update(int authGroupSeq, String name, String description, String arrayAuth ,String menuSeq) {
        AdminGroup AdminGroup = null;
        
        System.out.println("authGroupSeq: " + authGroupSeq);
        System.out.println("gname: " + name);
        System.out.println("userGroup: " + description);
        System.out.println("auth: " + arrayAuth);
        System.out.println("auth: " + menuSeq);

        try {
            /* 그룹 등록 처리 START */
            AdminGroup = new AdminGroup();
            AdminGroup.setAdminGroupSeq(authGroupSeq);
            AdminGroup.setName(name);
            AdminGroup.setDescription(description);
            AdminGroup.setModDate(new Date());

            AdminGroup = authGroupRepository.save(AdminGroup);
            /* 그룹 등록 처리 END */

            /* 그룹 권한 등록 처리 START */
            this.adminGroupAuthChange(authGroupSeq, arrayAuth, menuSeq);
            /* 그룹 권한 등록 처리 END */
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return AdminGroup;
    }

    @Transactional
    public void delete(int adminGroupSeq) {
        // AdminGroupAuth adminGroupAuth = null;
        // JSONObject jsonObject = null;
        
        System.out.println("번호 : " + adminGroupSeq);
        try {
            /* 그룹 권한 삭제 처리 START */
            adminGroupAuthRepository.deleteAllByAdminGroupSeqEquals(adminGroupSeq);
            /* 그룹 권한 삭제 처리 END */

            /* 그룹 삭제 처리 START */
            authGroupRepository.deleteById(adminGroupSeq);
            /* 그룹 삭제 처리 END */
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List<AdminGroupAuth> getMenuAuthList(int adminGroupSeq) {
        List<AdminGroupAuth> adminGroupAuthList = new ArrayList<>();

        try {

            List<AdminGroupAuth> source = adminGroupAuthRepository.findByAdminGroupSeq(adminGroupSeq, Sort.by(Sort.Direction.ASC, "seq"));

            System.out.println("DB 데이터 :"+source);

            Menu menu = null;
            for(int i=0; i<source.size(); i++) {
                // menu = menuList.get(i);
                adminGroupAuthList = source;

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return adminGroupAuthList;
    }

    // private void menuCheck(List<AdminGroupAuth> target, Menu menu, List<AdminGroupAuth> source) {
    //     try {
    //         AdminGroupAuth adminGroupAuth = new AdminGroupAuth();
    //         String menuAuth = "N";

    //         for(int i=0; i<source.size(); i++) {
    //             if(menu.getMenuSeq() == source.get(i).getMenuSeq()) {
    //                 menuAuth = source.get(i).getAuth();

    //                 break;
    //             }
    //         }

    //         adminGroupAuth.setMenuSeq(menu.getMenuSeq());
    //         adminGroupAuth.setAuth(menuAuth);
    //         adminGroupAuth.setMenu(menu);

    //         target.add(adminGroupAuth);
    //     } catch(Exception e) {
    //         logger.error(e.getMessage());
    //     }
    // }

    private void adminGroupAuthChange(Integer adminGroupSeq, String arrayAuth, String menuSeq) {
        try {
            AdminGroupAuth adminGroupAuth = null;

            System.out.println("번호 : " + adminGroupSeq);

            arrayAuth=arrayAuth.substring(1,arrayAuth.lastIndexOf("]"));
            menuSeq=menuSeq.substring(1,menuSeq.lastIndexOf("]"));
            String[] array = arrayAuth.split(",");
            String[] arrayMenuSeq = menuSeq.split(",");

            for(int i=0;i<array.length;i++) {
                array[i]=array[i].substring(1, array[i].length() - 1);
                arrayMenuSeq[i]=arrayMenuSeq[i].substring(1, arrayMenuSeq[i].length() - 1);

                adminGroupAuth = new AdminGroupAuth();
                adminGroupAuth.setAdminGroupSeq(adminGroupSeq);
                adminGroupAuth.setMenuSeq(Integer.parseInt(arrayMenuSeq[i]));
                adminGroupAuth.setAuth(array[i]);
                adminGroupAuth.setRegDate(new Date());
                adminGroupAuth.setModDate(new Date());

                adminGroupAuthRepository.save(adminGroupAuth);
                }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
