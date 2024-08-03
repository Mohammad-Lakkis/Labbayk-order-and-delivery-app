using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class RestaurantsRepository : IRestaurantsRepository
    {
        private readonly DataContext _context;

        public RestaurantsRepository(DataContext context)
        {
            _context = context;
        }

        public ICollection<Restaurant> GetRestaurants()
        {
            return _context.Restaurants.OrderBy(r => r.Name).ToList();
        }
    }
}
