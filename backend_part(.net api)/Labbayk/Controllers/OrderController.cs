using AutoMapper;
using Labbayk.Dto;
using Labbayk.Interfaces;
using Labbayk.Models;
using Labbayk.Repository;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OrderController : Controller
    {
        private readonly IOrdersRepository _ordersRepository;
        private readonly IMapper _mapper;

        public OrderController(IOrdersRepository ordersRepository, IMapper mapper)
        {
            _ordersRepository = ordersRepository;
            _mapper = mapper;
        }

        [HttpPost]
        [ProducesResponseType(204)]
        [ProducesResponseType(400)]
        public IActionResult CreateOrder([FromBody] OrderDto orderCreate, [FromQuery] ICollection<ItemDto> items, [FromQuery] string driverName)
        {
            if (orderCreate == null)
                return BadRequest(ModelState);

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var order = _mapper.Map<Order>(orderCreate);

            if (!_ordersRepository.CreateOrder(items,order,driverName))
            {
                ModelState.AddModelError("", "Something went wrong while saving");
                return StatusCode(500, ModelState);
            }

            return Ok("Successfully Created");
        }

        [HttpGet("{name}")]
        [ProducesResponseType(200, Type = typeof(Order))]
        [ProducesResponseType(400)]
        public IActionResult GetOrder(string name)
        {
            var order = _ordersRepository.GetOrder(name);

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(order);
        }

        [HttpGet("{driverId}/get")]
        [ProducesResponseType(200, Type = typeof(Order))]
        [ProducesResponseType(400)]
        public IActionResult GetOrderByDriver(int driverId)
        {
            ICollection<Order> orders = _ordersRepository.GetOrdersByDriver(driverId);

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(orders);
        }

        [HttpDelete("{customerName}")]
        [ProducesResponseType(400)]
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        public IActionResult DeleteOrder(string customerName) 
        {
            _ordersRepository.DeleteOrder(customerName);
            return Ok();
        }
    }
}
