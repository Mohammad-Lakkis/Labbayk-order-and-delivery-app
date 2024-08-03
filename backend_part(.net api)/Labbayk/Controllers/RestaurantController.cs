using Labbayk.Interfaces;
using Labbayk.Models;
using Labbayk.Repository;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RestaurantController : Controller
    {
        private readonly IRestaurantsRepository _restaurantsRepository;

        public RestaurantController(IRestaurantsRepository restaurantsRepository)
        {
            _restaurantsRepository = restaurantsRepository;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Restaurant>))]
        public IActionResult GetRestaurants()
        {
            var restaurants = _restaurantsRepository.GetRestaurants();

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(restaurants);
        }
    }
}
