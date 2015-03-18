package teamtwo.event.com.events;

/**
 * Created by Primož Pesjak on 18.3.2015.
 */
public class SearchResult{
        private String name = "";
        private String date = "";
        private String city = "";
        private String type = "";

        public void setName(String Name) {
            this.name = Name;
        }

        public String getName() {
            return name;
        }

        public void setDate(String Date) {
            this.date = Date;
        }

        public String getDate() {
            return date;
        }

        public void setCity(String City) {
            this.city = City;
        }

        public String getCity() {
            return city;
        }

        public void setType(String Type) {
            this.city = Type;
        }

        public String getType() {
            return type;
        }
}
