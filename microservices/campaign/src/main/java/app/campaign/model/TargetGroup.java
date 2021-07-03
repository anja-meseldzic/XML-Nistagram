package app.campaign.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TargetGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<AgeGroup> ageGroups;
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Gender> genders;

    public TargetGroup() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<AgeGroup> getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(Set<AgeGroup> ageGroups) {
        this.ageGroups = ageGroups;
    }

    public Set<Gender> getGenders() {
        return genders;
    }

    public void setGenders(Set<Gender> genders) {
        this.genders = genders;
    }
}
