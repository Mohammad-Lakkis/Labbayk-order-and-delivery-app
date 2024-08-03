using AutoMapper;
using Labbayk.Dto;
using Labbayk.Interfaces;
using Labbayk.Models;
using Microsoft.AspNetCore.Mvc;

namespace Labbayk.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ItemController : Controller
    {
        private readonly IItemsRepository _itemsRepository;
        private readonly IMapper _mapper;

        public ItemController(IItemsRepository itemsRepository, IMapper mapper)
        {
            _itemsRepository = itemsRepository;
            _mapper = mapper;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        public IActionResult GetItems() 
        {
            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetItems());

            if (!ModelState.IsValid)
                return BadRequest(ModelState);
            return Ok(items);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(200, Type = typeof(Item))]
        [ProducesResponseType(400)]
        public IActionResult GetItem(int id)
        {
            if (!_itemsRepository.IsAvailable(id))
                return NotFound();

            var item = _mapper.Map<ItemDto>(_itemsRepository.GetItem(id));
            
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(item);
        }

        [HttpGet("{title}/search")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        [ProducesResponseType(400)]
        public IActionResult SearchForItems(string title)
        {
            if (!_itemsRepository.IsAvailable(title))
                return NotFound();

            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetItems(title));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(items);
        }

        [HttpGet("BestFood")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        [ProducesResponseType(400)]
        public IActionResult GetBestFood()
        {
            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetBestFood());

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(items);
        }

        [HttpGet("{category}/get")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        [ProducesResponseType(400)]
        public IActionResult GetItemsByCategory(string category)
        {
            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetItemsByCategory(category));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(items);
        }

        [HttpGet("{priceId}/getByPrice")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        [ProducesResponseType(400)]
        public IActionResult GetItemsByPrice(int priceId)
        {
            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetItemsByPrice(priceId));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(items);
        }

        [HttpGet("{timeId}/getByTime")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Item>))]
        [ProducesResponseType(400)]
        public IActionResult GetItemsByTime(int timeId)
        {
            var items = _mapper.Map<List<ItemDto>>(_itemsRepository.GetItemsByTime(timeId));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(items);
        }
    }
}
