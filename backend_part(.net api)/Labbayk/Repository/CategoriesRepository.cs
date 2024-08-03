using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class CategoriesRepository : ICategoriesRepository
    {

        private readonly DataContext _context;

        public CategoriesRepository(DataContext context)
        {
            _context = context;
        }

        public ICollection<Category> GetCategories()
        {
            return _context.Categories.OrderBy(c => c.Id).ToList();
        }
    }
}
