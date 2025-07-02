package com.cognizant.ormlearn.entity;

import javax.persistence.*;

@Entity
@Table(name = "attempt_option")
public class AttemptOption {

    @Id
    @Column(name = "ao_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ao_aa_id")
    private AttemptQuestion attemptQuestion;

    @ManyToOne
    @JoinColumn(name = "ao_op_id")
    private Option option;

    @Column(name = "ao_selected")
    private boolean selected;

    public int getId() {
        return id;
    }

    public AttemptQuestion getAttemptQuestion() {
        return attemptQuestion;
    }

    public Option getOption() {
        return option;
    }

    public boolean isSelected() {
        return selected;
    }
}
