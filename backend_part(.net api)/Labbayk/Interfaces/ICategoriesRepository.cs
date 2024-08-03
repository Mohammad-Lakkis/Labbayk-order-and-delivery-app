using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface ICategoriesRepository
    {
        ICollection<Category> GetCategories();
    }
}
