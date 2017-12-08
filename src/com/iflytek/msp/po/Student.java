package com.iflytek.msp.po;

public class Student {
    private String name;
    private String age;

    private Grade grade;

    public class Grade { // 内部类要定义成public的
        private String course;
        private String score;
        private String level;

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        // 重写toString方法
        @Override
        public String toString() {
            return "Grade:[course = " + course + ", score = " + score
                    + ", level = " + level + "]";
        }
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "Student:[name = " + name + ", age = " + age + ", grade = "
                + grade + "]";
    }
}
