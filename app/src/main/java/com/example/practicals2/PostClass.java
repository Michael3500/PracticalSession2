package com.example.practicals2;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "MyPosts")
public class PostClass extends Model {

    @Column(name = "Title")
    public String title;

    @Column(name = "Description")
    public String description;

    @Column(name = "Section")
    public String section;

    @Column(name = "ImageDirectory")
    public String imageDirectory;

    public PostClass() {
        super();
    }

    public PostClass(String myTitle, String myDescription, String mySection, String myImage){
        super();
        this.title = myTitle;
        this.description = myDescription;
        this.section = mySection;
        this.imageDirectory = myImage;
    }

    public static List<PostClass> getAllPosts(String currentSection){
        //querying from the database to return the records which have section the same as the parameter
        return new Select().from(PostClass.class).where("Section = ?", currentSection).execute();
        //return new Select().from(PostClass.class).execute();
    }

    public static void savePost(String sTitle, String sDescription, String sSection, String sImage){
        PostClass pc = new PostClass(sTitle, sDescription, sSection, sImage);
        pc.save();
    }


}