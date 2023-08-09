package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.User;
import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;




    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save( Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    @Override
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category upDate(Category category) {
      return null;

    }

    @Override
    public void deleteById(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(category1 -> {
            category1.setDeleted(true);
            categoryRepository.save(category1);
        });

    }

    @Override
    public void enableById(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(category1 -> {
            category1.setDeleted(false);
            categoryRepository.save(category1);
        });
    }


    @Override
    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
   }



    @Override
    public List<Category> getAllCategories() {
        return   categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }




    @Override
    public void updateCategory(UUID id,Category category) {
        Optional<Category> newCategory = categoryRepository.findById(id);
        Category upDate = newCategory.get();
        upDate.setName(category.getName());

    }


    @Override
    public Category getCategory(UUID uuid) {
        return categoryRepository.findById(uuid).orElse(null);
    }


}
