package com.mobilepark.airtalk.service;

import com.mobilepark.airtalk.data.AdminGroup;
import com.mobilepark.airtalk.data.AdminGroupAuth;
import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.repository.AdminGroupRepository;
import com.mobilepark.airtalk.repository.AdminGroupAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminGroupService {
    private static final Logger logger = LoggerFactory.getLogger(AdminGroupService.class);

    @Autowired
    AdminGroupRepository authGroupRepository;

    @Autowired
    AdminGroupAuthRepository adminGroupAuthRepository;

    @Autowired
    SpecificationService<AdminGroup> specificationService;

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

    private Specification<AdminGroup> getSpecification(String type, String search) {
    
        AdminGroup AdminGroup = new AdminGroup();
        Set<String> likeSet = new HashSet<>();
    
        if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "name")) {
          logger.info("success service");
          AdminGroup.setName(search);
          likeSet.add(type);
        }
        else{
            logger.info("success service");
            AdminGroup.setDescription(search);
            likeSet.add(type);
        }
    
        return specificationService.like(likeSet, AdminGroup);
      }
    
        /**
         * 검색 조회
         * 
         * @return List<Code>
         */
        public List<AdminGroup> search(JSONObject form) {
    
            List<AdminGroup> list = new ArrayList<>();
    
            String type = form.get("type").toString();
            String keyword = form.get("keyword").toString();
            int length = Integer.parseInt(form.get("length").toString());
            int start = Integer.parseInt(form.get("start").toString());

            PageRequest pageRequest = PageRequest.of(start, length, Sort.by("regDate"));
            if (type == "all")
                list = authGroupRepository.findAll(pageRequest).getContent();
            else
                list = authGroupRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();
    
            return list;
        }
    
        /**
         * 검색 카운트 조회
         * 
         * @return Integer
         */
      public int count(JSONObject form) {
        int count = 0;
        switch(form.get("type").toString()) {
          case "all":
            count = authGroupRepository.countByAll();
            break;
          case "name":
            count = authGroupRepository.countByNameContaining(form.get("keyword").toString());
            break;
          case "description":
            count = authGroupRepository.countByDescriptionContaining(form.get("keyword").toString());
            break;
        } 
        logger.info("Total Count : ["+count+"]");
        return count;

      }
    
}
