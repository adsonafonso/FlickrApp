# FlickrApp

MVVM Architecture that uses the Paging Library

Application that searches the Flickr Api for Photos whose title, description or tags contain the text will be returned. 

The application uses a scroll listener for the RecyclerView and it performs lazy loading once the user scrolls past a certain point.

The Database is the main source of truth for the UI and all the searches get inserted into it. If the device is offline and the user searches for a recent search term he will get the cached version.

MVVM Architecture that uses the Paging Library

[![bleacherreport.png](https://i.postimg.cc/pXBtqvg0/bleacherreport.png)](https://postimg.cc/4mnMxD4t)
