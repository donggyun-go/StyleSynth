package FinalPrjTest.FinalPrj.domain;

import java.util.*;

public class DesignerData {

    private Designer designer;
    private List<Designerphoto> designerphotoList;
    private List<Review> reviews;

    public DesignerData(Designer designer, List<Designerphoto> designerphotoList, List<Review> reviews) {
        this.designer = designer;
        this.designerphotoList = designerphotoList;
        this.reviews = reviews;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public List<Designerphoto> getDesignerphotoList() {
        return designerphotoList;
    }

    public void setDesignerphotoList(List<Designerphoto> designerphotoList) {
        this.designerphotoList = designerphotoList;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


}
