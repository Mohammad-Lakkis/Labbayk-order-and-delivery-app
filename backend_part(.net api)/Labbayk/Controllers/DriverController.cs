using Labbayk.Dto;
using Labbayk.Interfaces;
using Labbayk.Models;
using Labbayk.Repository;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DriverController : Controller
    {
        private readonly IDriversRepository _driversRepository;
        public DriverController(IDriversRepository driversRepository)
        {
            _driversRepository = driversRepository;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Driver>))]
        public IActionResult GetDrivers()
        {
            var drivers = _driversRepository.GetDrivers();

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(drivers);
        }

        [HttpGet("{name}")]
        [ProducesResponseType(200, Type = typeof(Driver))]
        [ProducesResponseType(400)]
        public IActionResult GetDriverByName(string name)
        {


            var driver = _driversRepository.GetDriverByName(name);

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(driver);
        }


    }
}
