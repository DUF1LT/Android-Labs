package by.belstu.narkevich.lab14.data

interface IRouteRepository {
    fun saveRoute(route: Route)

    fun getRoutes(): ArrayList<Route>

    fun updateRoute(updatedRoute: Route)
}