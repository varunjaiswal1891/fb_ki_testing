package com.varun.fbproj.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	    private int userID;
		private String emailID;
		private String password;
		private String fname;
		private String lname;
		private String date;
		private String pic;
		private String mob_no;
		private String college;
		private String placeOfWork;
		private String hometown;
		private String cityOfWork;
		private String highschool;
		private String graduateSchool;
		
		public User()
		{
			
		}


		public User(int userID,String emailID,String password,
				String fname,String lname,String date,String pic,
				String mob_no,String college,
				String placeOfWork,String hometown,
				String cityOfWork,String highschool,			
				String graduateSchool
				) {
			super();
			this.userID=userID;
			this.emailID = emailID;
			this.password = password;
			this.fname = fname;
			this.lname=lname;
			this.date=date;
			this.pic = pic;
			this.mob_no=mob_no;
			this.college=college;
			this.placeOfWork=placeOfWork;
			this.hometown = hometown;
			this.cityOfWork=cityOfWork;
			this.highschool=highschool;
			this.graduateSchool=graduateSchool;
		}

		@JsonProperty(value = "userID")
		public int getUserID() {
			return userID;
		}


		public void setUserID(int userID) {
			this.userID = userID;
		}

		@JsonProperty(value = "emailID")
		public String getEmailID() {
			return emailID;
		}


		public void setEmailID(String emailID) {
			this.emailID = emailID;
		}

		@JsonProperty(value = "password")
		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}



		@JsonProperty(value = "fname")
		public String getFname() {
			return fname;
		}


		public void setFname(String fname) {
			this.fname = fname;
		}

		@JsonProperty(value = "lname")
		public String getLname() {
			return lname;
		}


		public void setLname(String lname) {
			this.lname = lname;
		}



		@JsonProperty(value = "date")
		public String getDate() {
			return date;
		}


		public void setDate(String date) {
			this.date = date;
		}


		public String getPic() {
			return pic;
		}


		public void setPic(String pic) {
			this.pic = pic;
		}

		@JsonProperty(value = "mob_no")
		public String getMob_no() {
			return mob_no;
		}


		public void setMob_no(String mob_no) {
			this.mob_no = mob_no;
		}


		@JsonProperty(value = "college")
		public String getCollege() {
			return college;
		}


		public void setCollege(String college) {
			this.college = college;
		}


		public String getPlaceOfWork() {
			return placeOfWork;
		}


		public void setPlaceOfWork(String placeOfWork) {
			this.placeOfWork = placeOfWork;
		}


		public String getHometown() {
			return hometown;
		}


		public void setHometown(String hometown) {
			this.hometown = hometown;
		}


		public String getCityOfWork() {
			return cityOfWork;
		}


		public void setCityOfWork(String cityOfWork) {
			this.cityOfWork = cityOfWork;
		}


		public String getHighschool() {
			return highschool;
		}


		public void setHighschool(String highschool) {
			this.highschool = highschool;
		}


		public String getGraduateSchool() {
			return graduateSchool;
		}


		public void setGraduateSchool(String graduateSchool) {
			this.graduateSchool = graduateSchool;
		}

/*		@Override
		    public boolean equals(Object obj) {
		        return (this.password.equals(((User) obj).password) && 
		        		(this.emailID.equals(((User) obj).emailID)) &&
		                (this.fname.equals(((User) obj).fname)) &&
		                (this.lname.equals(((User) obj).lname))&&
		                (this.date.equals(((User) obj).date))&&
		                (this.pic.equals(((User) obj).pic))&&
		                (this.college.equals(((User) obj).college))&&
		                (this.mob_no.equals(((User) obj).mob_no))&&
		                (this.placeOfWork.equals(((User) obj).placeOfWork))&&
		                (this.hometown.equals(((User) obj).hometown))&&
		                (this.highschool.equals(((User) obj).highschool))&&
		                (this.cityOfWork.equals(((User) obj).cityOfWork))
		                
		        		);
		    }
*/

	
}//class ends here
