using Labbayk.Interfaces;
using Labbayk.Models;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TimeController : Controller
    {
        private readonly ITimesRepository _timesRepository;

        public TimeController(ITimesRepository timesRepository)
        {
            _timesRepository = timesRepository;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Time>))]
        public IActionResult GetCategories()
        {
            var times = _timesRepository.GetTimes();

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(times);
        }
    }
}
