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
    public class CategoryController : Controller
    {
        private readonly ICategoriesRepository _categoriesRepository;

        public CategoryController(ICategoriesRepository categoriesRepository)
        {
            _categoriesRepository = categoriesRepository;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Category>))]
        public IActionResult GetCategories()
        {
            var categories = _categoriesRepository.GetCategories();

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(categories);
        }

        
    }
}
