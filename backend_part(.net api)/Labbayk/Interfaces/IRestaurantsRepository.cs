using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface IRestaurantsRepository
    {
        ICollection<Restaurant> GetRestaurants();
    }
}
