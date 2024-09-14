package com.example.thawaq.Service;

import com.example.thawaq.Api.ApiException;
import com.example.thawaq.Model.*;
import com.example.thawaq.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ExpertRepository expertRepository;
    private final BranchRepository branchRepository;
    private final MenuRepository menuRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // v2
    public void addRatingFromUserToStore(Integer userId, Integer storeId,Rating rating){
    User u = userRepository.findUserById(userId);
    Store s = storeRepository.findStoreById(storeId);

    if(u == null)
    {
        throw new ApiException("User not found");
    }
    if(s == null)
    {
        throw new ApiException("Store not found");
    }
    rating.setStore(s);
    rating.setClient(rating.getClient());
    userRepository.save(u);
    ratingRepository.save(rating);
    }

//    public void addRatingToStore(Rating rating) {
//        ratingRepository.save(rating);}

    // v2
    public void addRatingFromExpertToStore(Integer expertId, Integer storeId,Rating rating){
        Expert e = expertRepository.findExpertById(expertId);
        Store s = storeRepository.findStoreById(storeId);

        if(e == null)
        {
            throw new ApiException("Expert not found");
        }
        if(s == null)
        {
            throw new ApiException("Store not found");
        }
        rating.setStore(s);
        rating.setExpert(e);
        expertRepository.save(e);
        ratingRepository.save(rating);
    }

    public void updateRating(Rating rating,Integer id) {
        Rating r = ratingRepository.findRatingById(id);
        if(r == null) {
            throw new ApiException("Rating not found");}
        r.setService(rating.getService());
        r.setCleaning(rating.getCleaning());
        r.setQuality(rating.getQuality());
        r.setCost(rating.getCost());
        r.setComment(rating.getComment());
        r.setTitle(rating.getTitle());
        r.setAverageRating(rating.getAverageRating());
        ratingRepository.save(r);}



    public void deleteRating(Integer id) {
        Rating r = ratingRepository.findRatingById(id);
        if(r == null) {
            throw new ApiException("Rating not found");}
        ratingRepository.delete(r);
    }

    public List<Store> getBOTHByCleaningOfRating()  //v2
    {
        List<Store> s = storeRepository.findStoreByTypeOfActivity("BOTH");
        if(s.isEmpty())
        {
            throw new ApiException("Store (BOTH) is empty");
        }
//        if(s.size() == 1)
//        {
//            return s;
//        }
        int count;
        double d ;
        List<Rating> r = ratingRepository.findAll();
        Double[] aaSum = new Double[s.size()];
        Integer[] aaID = new Integer[s.size()];
        for (int i = 0; i < s.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r.get(j).getStore().getId() == s.get(i).getId())
                {
                    d += r.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s.get(i).getId();
            }
        }

        for (int i = 0; i < s.size(); i++) {
            for (int j = i; j < s.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
//        count = 0;
//        List<Store> ss2 = storeRepository.findAll();
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }

//        for (int i = 0; i < storeRepository.findAll().size(); i++) {
//            if(ss2.get(i).getId() == aaID[count])
//            {
//                sss2.add(ss2.get(i));
//                sss2.addAll(ss2);
//                count++;
//            }
//        }
        return sss2;
    }

    public List<Store> getRestaurantByCleaningOfRating()  //v2
    {
        List<Store> s = storeRepository.findStoreByTypeOfActivity("RESTAURANT");
        if(s.isEmpty())
        {
            throw new ApiException("Store (RESTAURANT) is empty");
        }
//        if(s.size() == 1)
//        {
//            return s;
//        }
        int count;
        double d ;
        List<Rating> r = ratingRepository.findAll();
        Double[] aaSum = new Double[s.size()];
        Integer[] aaID = new Integer[s.size()];
        for (int i = 0; i < s.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r.get(j).getStore().getId() == s.get(i).getId())
                {
                    d += r.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s.get(i).getId();
            }
        }

        for (int i = 0; i < s.size(); i++) {
            for (int j = i; j < s.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
//        count = 0;
//        List<Store> ss2 = storeRepository.findAll();
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }

//        for (int i = 0; i < storeRepository.findAll().size(); i++) {
//            if(ss2.get(i).getId() == aaID[count])
//            {
//                sss2.add(ss2.get(i));
//                sss2.addAll(ss2);
//                count++;
//            }
//        }
        return sss2;
    }
    public List<Store> getCafeByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("CAFE");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (CAFE) is empty");
        }
//        if(s2.size() == 1)
//        {
//            return s2;
//        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }

        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
//        count = 0;
//        List<Store> ss2 = storeRepository.findAll();
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
                sss2.add(storeRepository.findStoreById(aaID[i]));
        }

//        for (int i = 0; i < storeRepository.findAll().size(); i++) {
//            if(ss2.get(i).getId() == aaID[count])
//            {
//                sss2.add(ss2.get(i));
//                sss2.addAll(ss2);
//                count++;
//            }
//        }
        return sss2;
    }

    public Set<Branch> getBranchBOTHByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("BOTH");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (BOTH) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
//        if(s2.size() == 1)
//        {
//            branchSet.addAll(s2.get(0).getBranches());
//            return branchSet;
//        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }

        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        return branchSet;

//        List<Store> sss2 = new ArrayList<>();
//        for (int i = 0; i < aaID.length; i++) {
//            sss2.add(storeRepository.findStoreById(aaID[i]));
//        }
//        int wq = 0;
////        List<Branch> branchList2 = branchRepository.findAll();
//        for (int i = 0; i < sss2.size(); i++) {
//            for (int j = 0; j < branchRepository.findAll().size(); j++) {
//                if(sss2.get(i).getId() == branchRepository.findAll().get(j).getStore().getId())
//                {
//                    wq++;
////                    branchSet.add(branchRepository.findBranchById(branchRepository.findAll().get(j).getStore().getId()));
//                }
//            }
//        }
//        count = 0;
//        Integer[] bbID = new Integer[wq];
//        for (int i = 0; i < sss2.size(); i++) {
//            for (int j = 0; j < branchRepository.findAll().size(); j++) {
//                if(sss2.get(i).getId() == branchRepository.findAll().get(j).getStore().getId())
//                {
//                    bbID[count] = branchRepository.findAll().get(j).getStore().getId();
//                    count++;
//                }
//            }
//        }
//        for (int i = 0; i < bbID.length; i++) {
////            sss2.add(storeRepository.findStoreById(aaID[i]));
//            branchSet.add(branchRepository.findBranchById(bbID[i]));
//        }
////        for (int i = 0; i < sss2.size(); i++) {
////            branchSet.addAll(sss2.get(i).getBranches());
////        }
//        if(branchSet.isEmpty())
//        {
//            throw new ApiException("branchSet not found");
//        }
//        return branchSet;
    }
    public Set<Branch> getBranchRestaurantByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("RESTAURANT");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (BOTH) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
//        if(s2.size() == 1)
//        {
//            branchSet.addAll(s2.get(0).getBranches());
//            return branchSet;
//        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        return branchSet;
    }
    public Set<Branch> getBranchCafeByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("CAFE");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (CAFE) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
//        if(s2.size() == 1)
//        {
//            branchSet.addAll(s2.get(0).getBranches());
//            return branchSet;
//        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        return branchSet;
    }
    public Set<Menu> getMenuBOTHByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("BOTH");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (BOTH) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
        Set<Menu> menuSet = new HashSet<>();
        if(s2.get(0).getBranches().size() == 1)
        {
            branchSet.addAll(s2.get(0).getBranches());
            menuSet.addAll(menuRepository.findAll());
            return menuSet;
        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        for (int i = 0; i < branchSet.size(); i++) {
            menuSet.addAll(branchSet.stream().toList().get(i).getMenus());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        if(menuSet.isEmpty())
        {
            throw new ApiException("MenuSet not found");
        }
        return menuSet;
    }
    public Set<Menu> getMenuRestaurantByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("RESTAURANT");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (RESTAURANT) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
        Set<Menu> menuSet = new HashSet<>();
        if(s2.get(0).getBranches().size() == 1)
        {
            branchSet.addAll(s2.get(0).getBranches());
            menuSet.addAll(menuRepository.findAll());
            return menuSet;
        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        for (int i = 0; i < branchSet.size(); i++) {
            menuSet.addAll(branchSet.stream().toList().get(i).getMenus());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        if(menuSet.isEmpty())
        {
            throw new ApiException("MenuSet not found");
        }
        return menuSet;
    }
    public Set<Menu> getMenuCafeByCleaningOfRating()  //v2
    {
        List<Store> s2 = storeRepository.findStoreByTypeOfActivity("CAFE");
        if(s2.isEmpty())
        {
            throw new ApiException("Store (CAFE) is empty");
        }
        Set<Branch> branchSet = new HashSet<>();
        Set<Menu> menuSet = new HashSet<>();
        if(s2.get(0).getBranches().size() == 1)
        {
            branchSet.addAll(s2.get(0).getBranches());
            menuSet.addAll(menuRepository.findAll());
            return menuSet;
        }
        int count;
        double d ;
        List<Rating> r2 = ratingRepository.findAll();
        Double[] aaSum = new Double[s2.size()];
        Integer[] aaID = new Integer[s2.size()];
        for (int i = 0; i < s2.size(); i++) {
            count = 0;
            d = 0;
            for (int j = 0; j < ratingRepository.findAll().size(); j++) {
                if(r2.get(j).getStore().getId() == s2.get(i).getId())
                {
                    d += r2.get(j).getCleaning();
                    count++;
                }
                aaSum[i] = d/count;
                aaID[i] = s2.get(i).getId();
            }
        }
        for (int i = 0; i < s2.size(); i++) {
            for (int j = i; j < s2.size(); j++) {
                if(aaSum[i] <= aaSum[j])
                {
                    double temp1 = aaSum[i];
                    aaSum[i] = aaSum[j];
                    aaSum[j] = temp1;

                    int temp2 = aaID[i];
                    aaID[i] = aaID[j];
                    aaID[j] = temp2;
                }
            }
        }
        List<Store> sss2 = new ArrayList<>();
        for (int i = 0; i < aaID.length; i++) {
            sss2.add(storeRepository.findStoreById(aaID[i]));
        }
        for (int i = 0; i < sss2.size(); i++) {
            branchSet.addAll(sss2.get(i).getBranches());
        }
        for (int i = 0; i < branchSet.size(); i++) {
            menuSet.addAll(branchSet.stream().toList().get(i).getMenus());
        }
        if(branchSet.isEmpty())
        {
            throw new ApiException("branchSet not found");
        }
        if(menuSet.isEmpty())
        {
            throw new ApiException("MenuSet not found");
        }
        return menuSet;
    }
    public List<Rating> getRatingByLatest()  //v2
    {
        List<Rating> list = new ArrayList<>();

        for (int i = ratingRepository.findAll().size()-1; i >= 0; i--) {
            Rating temp;
            list.add(ratingRepository.findAll().get(i));
        }

        return list;
    }
    public List<Rating> getRatingByHigh()  //v2
    {
        List<Rating> list = ratingRepository.findAll();
        for (int i = 0; i < ratingRepository.findAll().size(); i++) {
            list.get(i).setAverageRating((list.get(i).getService()+list.get(i).getCost()+list.get(i).getQuality()+list.get(i).getCleaning())/4);
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if(list.get(i).getAverageRating() < list.get(j).getAverageRating())
                {
                    Rating temp = list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,temp);
                }
            }
        }
        return list;
    }
    public List<Rating> getRatingByLow()  //v2
    {
        List<Rating> list = ratingRepository.findAll();
        for (int i = 0; i < ratingRepository.findAll().size(); i++) {
            list.get(i).setAverageRating((list.get(i).getService()+list.get(i).getCost()+list.get(i).getQuality()+list.get(i).getCleaning())/4);
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if(list.get(i).getAverageRating() > list.get(j).getAverageRating())
                {
                    Rating temp = list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,temp);
                }
            }
        }
        return list;
    }
    public List<Rating> getRatingByComment(String comment)  //v2
    {
        if(ratingRepository.findRatingByCommentContains(comment).isEmpty())
        {
            throw new ApiException("Rating not found");
        }
        return ratingRepository.findRatingByCommentContains(comment);
    }

}
