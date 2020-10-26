package com.mobilepark.airtalk.service;

import com.mobilepark.airtalk.common.ResultMassage;
import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.data.Vacation;
import com.mobilepark.airtalk.data.type.VacationType;
import com.mobilepark.airtalk.repository.AdminRepository;
import com.mobilepark.airtalk.repository.VacationRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class VacationService {

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PageService pageService;

    @Autowired
    SpecificationService<Vacation> specificationService;

    VacationType vacationType;

    private static final Logger logger = LoggerFactory.getLogger(VacationService.class);

    // 상세 페이지
    public Vacation view(int vacationSeq) {
        try {
            Optional<Vacation> optionalVacation = vacationRepository.findById(vacationSeq);

            if (!optionalVacation.isPresent()) {
                return null;
            }

            return optionalVacation.get();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // 저장
    public ResultMassage create(Vacation vacation) {
        try {
            vacationRepository.save(vacation);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultMassage.FAIL;
        }
        return ResultMassage.SUCCESS;
    }

    // 수정
    public ResultMassage modify(Vacation vacation) {

        try {
            Optional<Vacation> vacationTemp = vacationRepository.findById(vacation.getVacationSeq());

            vacationTemp.ifPresent(selectVacation -> {

                selectVacation.setVacationSeq(vacation.getVacationSeq());
                selectVacation.setType(vacation.getType());
                selectVacation.setStartDate(vacation.getStartDate());
                selectVacation.setEndDate(vacation.getEndDate());
                selectVacation.setRank(vacation.getRank());
                selectVacation.setPhoneNum(vacation.getPhoneNum());
                selectVacation.setPeriod(vacation.getPeriod());
                selectVacation.setRegDate(vacation.getRegDate());
                selectVacation.setDescription(vacation.getDescription());

                selectVacation.setModDate(new Date());
                vacationRepository.save(selectVacation);
            });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultMassage.FAIL;
        }

        return ResultMassage.SUCCESS;
    }

    // 삭제
    public ResultMassage vacationRemove(Vacation vacation) {
        try {
            vacationRepository.delete(vacation);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultMassage.FAIL;
        }

        return ResultMassage.SUCCESS;

    }

    // 수정, 삭제 시 글 조회
    public Vacation findByVacationSeq(int vacationSeq) {
        Optional<Vacation> vacation = vacationRepository.findById(vacationSeq);

        if (!vacation.isPresent()) {
            return null;
        }

        return vacation.get();
    }

    // amdin ID 추출
    public String getAdminId(HttpSession session) {

        String adminInfo = session.getAttribute("ADMIN_INFO").toString();
        int index = adminInfo.indexOf("adminId");
        String adminId = null;
        adminId = adminInfo.substring(index + 8, (adminInfo.substring(index).indexOf(',') + index));

        return adminId;
    }


    // 페이지 이동 시 검색 값 유지를 위해
    public void changeName(Map<String, Object> searchRequest) {

        String keyword = searchRequest.toString();
        keyword = keyword.substring(keyword.indexOf("adminId=") + 8, keyword.indexOf("option=") - 2);

        Admin admin = adminRepository.findByAdminId(keyword);

        String temp = admin.toString();

        if (!("[]".equals(temp))) {
            temp = temp.substring(temp.indexOf("adminName=") + 10, temp.indexOf("password=") -2);

            searchRequest.replace("type", temp);
            searchRequest.replace("adminDate", temp);
            searchRequest.replace("startDate", temp);
            searchRequest.replace("endDate", temp);
            searchRequest.replace("period", temp);
            searchRequest.replace("rank", temp);
            searchRequest.replace("phoneNum", temp);
            searchRequest.replace("adminId", temp);
        }
    }

    // 직위 변환
    public String chageRank(String type) {
        String result = "";

        switch (type) {
            case "DH":
                result = "부장";
                break;

            case "HD":
                result = "차장";
                break;

            case "SC":
                result = "과장";
                break;

            case "DS":
                result = "대리";
                break;

            case "WO":
                result = "사원";
                break;

            default:
                result = "";
                break;
        }

        return result;
    }

    public String getWriterId (String keyword) {

        String temp = "";

        List<Admin> admin = adminRepository.findByAdminName(keyword);

        String check = admin.toString();

        try {
            if (!("[]".equals(check))) {
                for (int index = 0; index < admin.size(); index++) {
                    String getAdmin = admin.get(index).toString();

                    temp = getAdmin.substring(getAdmin.indexOf("adminId=") + 8, getAdmin.indexOf("adminName=") - 2);
                }
            }
        }
        catch (Exception e) {
            logger.error("getWriterId Exception :", e);
        }

        return temp;
    }

    /**
     * datatable count
     */
    public Long count() {
        long totalCount = 0;

        try {
            totalCount = vacationRepository.count();
        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return totalCount;
    }

    private Specification<Vacation> getSpecification(String search) {

        String option = "";
        String keyword = "";
        String startDate = "";
        String endDate = "";

        Date sDate = null;
        Date eDate = null;

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        if (StringUtils.isNotEmpty(search) && search.split("\\|").length == 2) {
            option = search.split("\\|")[0];
            keyword = search.split("\\|")[1];
        } else if (StringUtils.isNotEmpty(search) && search.split("\\|").length > 2) {
            option = search.split("\\|")[0];
            keyword = search.split("\\|")[1];
            startDate = search.split("\\|")[2];
            endDate = search.split("\\|")[3];
        }

        Vacation vacation = new Vacation();
        Set<String> likeSet = new HashSet<>();

        if (StringUtils.isNotEmpty(keyword) && StringUtils.equals(option, "adminName")) {
            String adminId = getWriterId(keyword);
            vacation.setAdminId(adminId);
            likeSet.add("adminId");

            if ((!"".equals(startDate)) && (!"".equals(endDate))) {
                return dateSearch(startDate, endDate, vacation, likeSet);
            }

            return specificationService.equals(likeSet, vacation);
        }

        else if (StringUtils.isNotEmpty(keyword) && StringUtils.equals(option, "type")) {
            if (("반차(오전)".equals(keyword)) || ("반차(오후)".equals(keyword)) || ("연차".equals(keyword)) || ("공가".equals(keyword)) || ("경조사".equals(keyword))) {
                String typeCode = vacationType.getType(keyword).getCode();
                vacation.setType(typeCode);
            }
            else {
                vacation.setType(keyword);
            }
            likeSet.add("type");

            if ((!"".equals(startDate)) && (!"".equals(endDate))) {
                return dateSearch(startDate, endDate, vacation, likeSet);
            }

            return specificationService.equals(likeSet, vacation);
        }

        else if (StringUtils.isNotEmpty(keyword) && StringUtils.equals(option, "period")) {
            vacation.setPeriod(keyword);
            likeSet.add("period");

            if ((!"".equals(startDate)) && (!"".equals(endDate))) {
                return dateSearch(startDate, endDate, vacation, likeSet);
            }

            return specificationService.equals(likeSet, vacation);
        }

        else if (StringUtils.equals(option, "list")){
            try {
                vacation.setStartDate(startDate);
                sDate = sdf.parse(startDate);
                eDate = sdf.parse(endDate);
                cal.setTime(eDate);
                cal.add(Calendar.DATE, +1);
                eDate = cal.getTime();

                endDate = sdf2.format(eDate);

                vacation.setEndDate(endDate);
            } catch (ParseException e) {
                logger.error("ParseException :", e);
            } catch (Exception e) {
                logger.error("Exception :", e);
            }
            return specificationService.between("regDate", sDate, eDate);
        }

        return specificationService.like(likeSet, vacation);
    }

    public Specification<Vacation> dateSearch (String startDate, String endDate, Vacation vacation, Set<String> likeSet) {
        Specification<Vacation> vacationSpec = null;

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        Date sDate = null;
        Date eDate = null;

        if ((!"".equals(startDate)) && (!"".equals(endDate))) {
            try {
                vacation.setStartDate(startDate);
                sDate = sdf.parse(startDate);
                eDate = sdf.parse(endDate);
                cal.setTime(eDate);
                cal.add(Calendar.DATE, +1);
                eDate = cal.getTime();

                endDate = sdf2.format(eDate);

                vacation.setEndDate(endDate);

            } catch (ParseException e) {
                logger.error("ParseException :", e);
            } catch (Exception e) {
                logger.error("Exception :", e);
            }

            vacationSpec = specificationService.between("regDate", sDate, eDate);
            vacationSpec = vacationSpec.and(specificationService.equals(likeSet, vacation));
            return vacationSpec;
        }

        return vacationSpec;
    }


    public Long count(String search) {

        Specification<Vacation> specs = this.getSpecification(search);

        return vacationRepository.count(specs);
    }

    public List<List<String>> search(String search,
                                     Map<String, String> sortValue,
                                     int start,
                                     int length) {

        PageRequest pageRequest = PageRequest.of(start/length, length,
                pageService.convertSort(sortValue));

        Specification<Vacation> specs = this.getSpecification(search);

        List<List<String>> datatable = new ArrayList<>();
        try {
            Page<Vacation> list = vacationRepository.findAll(specs, pageRequest);
            for (Vacation vacation : list) {
                List<String> row = new ArrayList<>();

                String type = vacation.getType();
                vacation.setType(vacationType.getType(type).getLabel());

                row.add(vacation.getVacationSeq() + "");
                row.add(vacation.getType());
                row.add(vacation.getStartDate());
                row.add(vacation.getEndDate());
                row.add(vacation.getPeriod());
                row.add(vacation.getAdmin().getAdminName());
                row.add(DateFormatUtils.format(vacation.getRegDate(), "yyyy-MM-dd HH:mm"));

                datatable.add(row);
            }
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return datatable;
    }
}