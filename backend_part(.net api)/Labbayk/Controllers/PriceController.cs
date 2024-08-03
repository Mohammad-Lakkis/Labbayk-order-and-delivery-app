using Labbayk.Interfaces;
using Labbayk.Models;
using Labbayk.Repository;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PriceController : Controller
    {
        private readonly IPricesRepository _pricesRepository;

        public PriceController(IPricesRepository pricesRepository)
        {
            _pricesRepository = pricesRepository;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Price>))]
        public IActionResult GetPrices()
        {
            var prices = _pricesRepository.GetPrices();

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(prices);
        }
    }
}
